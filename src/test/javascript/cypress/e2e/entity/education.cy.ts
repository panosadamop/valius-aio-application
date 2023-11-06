import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Education e2e test', () => {
  const educationPageUrl = '/education';
  const educationPageUrlPattern = new RegExp('/education(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const educationSample = { value: 'wireless', language: 'ENGLISH' };

  let education;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/educations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/educations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/educations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (education) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/educations/${education.id}`,
      }).then(() => {
        education = undefined;
      });
    }
  });

  it('Educations menu should load Educations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Education').should('exist');
    cy.url().should('match', educationPageUrlPattern);
  });

  describe('Education page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(educationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Education page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/education/new$'));
        cy.getEntityCreateUpdateHeading('Education');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', educationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/educations',
          body: educationSample,
        }).then(({ body }) => {
          education = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/educations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/educations?page=0&size=20>; rel="last",<http://localhost/api/educations?page=0&size=20>; rel="first"',
              },
              body: [education],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(educationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Education page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('education');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', educationPageUrlPattern);
      });

      it('edit button click should load edit Education page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Education');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', educationPageUrlPattern);
      });

      it('edit button click should load edit Education page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Education');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', educationPageUrlPattern);
      });

      it('last delete button click should delete instance of Education', () => {
        cy.intercept('GET', '/api/educations/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('education').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', educationPageUrlPattern);

        education = undefined;
      });
    });
  });

  describe('new Education page', () => {
    beforeEach(() => {
      cy.visit(`${educationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Education');
    });

    it('should create an instance of Education', () => {
      cy.get(`[data-cy="value"]`).type('Associate Jewelery').should('have.value', 'Associate Jewelery');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        education = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', educationPageUrlPattern);
    });
  });
});

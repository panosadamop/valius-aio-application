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

describe('Occupation e2e test', () => {
  const occupationPageUrl = '/occupation';
  const occupationPageUrlPattern = new RegExp('/occupation(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const occupationSample = { value: 'tolerance pink orchid', language: 'GREEK' };

  let occupation;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/occupations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/occupations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/occupations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (occupation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/occupations/${occupation.id}`,
      }).then(() => {
        occupation = undefined;
      });
    }
  });

  it('Occupations menu should load Occupations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('occupation');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Occupation').should('exist');
    cy.url().should('match', occupationPageUrlPattern);
  });

  describe('Occupation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(occupationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Occupation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/occupation/new$'));
        cy.getEntityCreateUpdateHeading('Occupation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', occupationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/occupations',
          body: occupationSample,
        }).then(({ body }) => {
          occupation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/occupations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/occupations?page=0&size=20>; rel="last",<http://localhost/api/occupations?page=0&size=20>; rel="first"',
              },
              body: [occupation],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(occupationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Occupation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('occupation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', occupationPageUrlPattern);
      });

      it('edit button click should load edit Occupation page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Occupation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', occupationPageUrlPattern);
      });

      it('edit button click should load edit Occupation page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Occupation');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', occupationPageUrlPattern);
      });

      it('last delete button click should delete instance of Occupation', () => {
        cy.intercept('GET', '/api/occupations/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('occupation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', occupationPageUrlPattern);

        occupation = undefined;
      });
    });
  });

  describe('new Occupation page', () => {
    beforeEach(() => {
      cy.visit(`${occupationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Occupation');
    });

    it('should create an instance of Occupation', () => {
      cy.get(`[data-cy="value"]`).type('Pa&#39;anga Account').should('have.value', 'Pa&#39;anga Account');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        occupation = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', occupationPageUrlPattern);
    });
  });
});

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

describe('Industry e2e test', () => {
  const industryPageUrl = '/industry';
  const industryPageUrlPattern = new RegExp('/industry(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const industrySample = { value: 'GB of', language: 'ENGLISH' };

  let industry;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/industries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/industries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/industries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (industry) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/industries/${industry.id}`,
      }).then(() => {
        industry = undefined;
      });
    }
  });

  it('Industries menu should load Industries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('industry');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Industry').should('exist');
    cy.url().should('match', industryPageUrlPattern);
  });

  describe('Industry page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(industryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Industry page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/industry/new$'));
        cy.getEntityCreateUpdateHeading('Industry');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', industryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/industries',
          body: industrySample,
        }).then(({ body }) => {
          industry = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/industries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/industries?page=0&size=20>; rel="last",<http://localhost/api/industries?page=0&size=20>; rel="first"',
              },
              body: [industry],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(industryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Industry page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('industry');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', industryPageUrlPattern);
      });

      it('edit button click should load edit Industry page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Industry');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', industryPageUrlPattern);
      });

      it('edit button click should load edit Industry page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Industry');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', industryPageUrlPattern);
      });

      it('last delete button click should delete instance of Industry', () => {
        cy.intercept('GET', '/api/industries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('industry').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', industryPageUrlPattern);

        industry = undefined;
      });
    });
  });

  describe('new Industry page', () => {
    beforeEach(() => {
      cy.visit(`${industryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Industry');
    });

    it('should create an instance of Industry', () => {
      cy.get(`[data-cy="value"]`).type('Steel').should('have.value', 'Steel');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        industry = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', industryPageUrlPattern);
    });
  });
});

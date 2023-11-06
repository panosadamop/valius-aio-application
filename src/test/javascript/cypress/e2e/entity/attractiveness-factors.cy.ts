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

describe('AttractivenessFactors e2e test', () => {
  const attractivenessFactorsPageUrl = '/attractiveness-factors';
  const attractivenessFactorsPageUrlPattern = new RegExp('/attractiveness-factors(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const attractivenessFactorsSample = { value: 'Kong Awesome Bahamian', language: 'GREEK' };

  let attractivenessFactors;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/attractiveness-factors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/attractiveness-factors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/attractiveness-factors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (attractivenessFactors) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/attractiveness-factors/${attractivenessFactors.id}`,
      }).then(() => {
        attractivenessFactors = undefined;
      });
    }
  });

  it('AttractivenessFactors menu should load AttractivenessFactors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('attractiveness-factors');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AttractivenessFactors').should('exist');
    cy.url().should('match', attractivenessFactorsPageUrlPattern);
  });

  describe('AttractivenessFactors page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(attractivenessFactorsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AttractivenessFactors page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/attractiveness-factors/new$'));
        cy.getEntityCreateUpdateHeading('AttractivenessFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', attractivenessFactorsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/attractiveness-factors',
          body: attractivenessFactorsSample,
        }).then(({ body }) => {
          attractivenessFactors = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/attractiveness-factors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/attractiveness-factors?page=0&size=20>; rel="last",<http://localhost/api/attractiveness-factors?page=0&size=20>; rel="first"',
              },
              body: [attractivenessFactors],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(attractivenessFactorsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AttractivenessFactors page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('attractivenessFactors');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', attractivenessFactorsPageUrlPattern);
      });

      it('edit button click should load edit AttractivenessFactors page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AttractivenessFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', attractivenessFactorsPageUrlPattern);
      });

      it('edit button click should load edit AttractivenessFactors page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AttractivenessFactors');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', attractivenessFactorsPageUrlPattern);
      });

      it('last delete button click should delete instance of AttractivenessFactors', () => {
        cy.intercept('GET', '/api/attractiveness-factors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('attractivenessFactors').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', attractivenessFactorsPageUrlPattern);

        attractivenessFactors = undefined;
      });
    });
  });

  describe('new AttractivenessFactors page', () => {
    beforeEach(() => {
      cy.visit(`${attractivenessFactorsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AttractivenessFactors');
    });

    it('should create an instance of AttractivenessFactors', () => {
      cy.get(`[data-cy="value"]`).type('Handmade').should('have.value', 'Handmade');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        attractivenessFactors = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', attractivenessFactorsPageUrlPattern);
    });
  });
});
